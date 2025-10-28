# Testing Guide - Online Steganography Project

## What Was Fixed

1. ✅ **Fixed "Reveal Hidden Message" button** - Now properly decodes and shows hidden text
2. ✅ **Fixed image download functionality** - Download button now works
3. ✅ **Fixed steganography decoding** - Both Java backend and JavaScript frontend now use the same LSB algorithm
4. ✅ **Added upload-decode feature** - You can upload any encoded image to decode it

## How to Test

### Step 1: Start the Application
```bash
cd C:\Users\nakka\Downloads\OnlineStegoProject
java -jar target\online-stego-0.0.1-SNAPSHOT.jar
```

Or run in your IDE (Eclipse/IntelliJ)

### Step 2: Create Test Users
1. Go to: http://localhost:8081/register
2. Create User 1 (e.g., name: "TestUser1")
3. Log out and create User 2 (e.g., name: "TestUser2")

### Step 3: Send a Secret Message
1. Login as User 1
2. Click on User 2 in the list
3. Upload an image (PNG or JPG - larger images work better)
4. Enter message: "Hi 😊 This is a test message"
5. Click "Send Secret Message"

### Step 4: Test Reveal Hidden Message
1. Logout and login as User 2
2. Open the chat with User 1
3. You should see the encoded image
4. Click **"Reveal Hidden Message"** button
5. ✅ You should see: "Hi 😊 This is a test message"

### Step 5: Test Download
1. In the same chat, click **"Download Image"** button
2. ✅ The image should download to your Downloads folder
3. File will be named: `encoded-message-{id}.png`

### Step 6: Test Upload-Decode
1. In the "Decode Any Image" section on the right
2. Click "Choose File" and select the downloaded image
3. Click **"Decode Message"**
4. ✅ You should see: "Hi 😊 This is a test message"

### Step 7: Test with External Steganography Tool
1. Download the encoded image from Step 5
2. Upload it to ANY online steganography decoder
3. ✅ It should reveal the same hidden message

## What the System Does NOT Store

✅ **Messages are NEVER stored in database**
- Only image path is stored in `messagesdb` table
- The actual message text is ONLY in the encoded image
- Even if someone accesses the database, they can't see the message

✅ **Security Features**
- Message text is embedded using LSB (Least Significant Bit) steganography
- No plaintext storage anywhere
- Images can be downloaded and decoded on any device

## Troubleshooting

### If "Reveal" shows nothing:
1. Check browser console (F12) for errors
2. Make sure image file exists in `uploads/` folder
3. Check server logs for error messages

### If download doesn't work:
1. Try right-clicking on image → "Save image as..."
2. Check browser settings for downloads
3. Look for popup blockers

### If decode shows "No hidden message":
1. Make sure you're using an image encoded by THIS tool
2. Try with a larger image (small images may not have capacity)
3. Check that image wasn't modified/resaved after encoding

## Technical Details

### Encoding Process
1. User uploads image + enters text
2. Backend reads text bytes
3. Embed text in blue channel LSB
4. Save as PNG (preserves quality)
5. Only save image path to database

### Decoding Process
1. Read first 32 bits = message length
2. Read next bits = message content
3. Convert bytes to UTF-8 string
4. Display hidden message

### Why PNG?
- PNG is lossless (JPG compression destroys hidden data)
- Always converts to PNG for output
- Preserves exact pixel values needed for LSB

## Expected Results

✅ "Reveal Hidden Message" button works
✅ Downloaded images decode correctly
✅ Upload-decode works for any encoded image
✅ Same images decode on external tools
✅ No message text in database (security)

