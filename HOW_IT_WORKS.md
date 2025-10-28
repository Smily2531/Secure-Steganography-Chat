# How Steganography Image Conversion Works

## The Complete Flow

### Step 1: User Uploads Image
```
User Selects: cartoon.jpg (for example)
↓
Saved to: uploads/orig_a1b2c3d4_cartoon.jpg
```

### Step 2: Message Hiding Process
```
Original Image + Secret Message ("Hi 😊")
         ↓
    LSB Encoding
         ↓
Steganography Image: uploads/enc_a1b2c3d4_cartoon.png
```

### Step 3: What Happens Technically

#### **Encoding (StegoUtil.encodeText)**
```java
1. Read original image pixels
2. Convert message to bytes: "Hi 😊" → [72, 105, 32, 240, 159, 152, 138]
3. Store 4 bytes for message length first
4. Embed each bit in the blue channel's LSB (Least Significant Bit)
5. Save as PNG (lossless format - preserves exact pixel values)
```

#### **What is LSB?**
- Every pixel has RGB values (0-255 each)
- Blue channel: 255 = 11111111 in binary
- We change ONLY the last bit: 11111110 or 11111111
- This 1-bit change is invisible to human eye
- No visible difference between original and stego image

### Step 4: Files Created

```
uploads/
├── orig_a1b2c3d4_cartoon.jpg    ← Original (kept for reference)
└── enc_a1b2c3d4_cartoon.png     ← Steganography image (sent to receiver)
```

### Step 5: What Gets Stored in Database

```sql
INSERT INTO messagesdb (imagePath, sender_id, receiver_id, sentAt)
VALUES ('/uploads/enc_a1b2c3d4_cartoon.png', 1, 2, '2024-01-01 10:00:00')
```

✅ **Only image path is stored - NO message text!**

### Step 6: Receiver Views Message

```
1. User sees: enc_a1b2c3d4_cartoon.png (appears like normal image)
2. Clicks "Reveal Hidden Message" button
3. System reads LSB bits from blue channel
4. Extracts first 32 bits = message length
5. Extracts remaining bits = message content
6. Displays: "Hi 😊"
```

### Step 7: Download & External Decode

```
Downloaded Image → Can be decoded by:
✅ This application (server-side or upload-decode)
✅ Any other LSB steganography decoder
✅ Standard steganography tools
```

## Visual Example

### Before Encoding (Original Image)
```
Pixel (100, 100): RGB(255, 200, 150)
                  Binary: 11111111, 11001000, 10010110
                  Blue:   10010110
```

### After Encoding (Steganography Image)
```
Pixel (100, 100): RGB(255, 200, 150 or 151)
                  Binary: 11111111, 11001000, 10010110 or 10010111
                  Blue:   10010110 or 10010111 (1-bit change)
                  
Looks identical to human eye! 👁️
```

## Key Features

✅ **Image Format Conversion**
- Input: JPG, PNG, or any image format
- Output: Always PNG (to preserve quality and hidden data)
- Original saved as "orig_xxx"
- Encoded saved as "enc_xxx.png"

✅ **Security**
- Message is embedded IN the image pixels
- Not stored anywhere else (not in DB, not in files)
- Only image path in database
- Even database hackers can't see the message

✅ **Compatibility**
- Works with standard LSB steganography tools
- Can decode on any device
- Format: Standard LSB (Least Significant Bit)

## File Naming Convention

```
Original:  orig_{UUID}_{original_filename}
Encoded:   enc_{UUID}_{original_filename_without_ext}.png

Example:
  User uploads: "myphoto.jpg"
  System creates:
    - orig_a1b2c3d4-myphoto.jpg
    - enc_a1b2c3d4-myphoto.png
```

## Technical Details

### Why PNG?
- **Lossless compression** - preserves exact pixel values
- **RGB mode** - each pixel has exact R, G, B values
- **No quality loss** - hidden data stays intact
- JPG uses lossy compression which destroys hidden data

### Capacity
- Each pixel = 1 bit of hidden data
- 100x100 image = 10,000 bits = 1,250 bytes max message
- First 32 bits reserved for message length
- So 1,246 bytes for actual message text

### Algorithm Details
- LSB (Least Significant Bit) embedding
- Blue channel only (RGB)
- Row-by-row, left-to-right bit embedding
- Standard steganography technique

