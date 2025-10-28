OnlineStegoProject - Minimal Spring Boot steganography messaging app

SECURITY FEATURES:
✓ Messages are NEVER stored in database - only encoded images are saved
✓ Text is embedded directly in the image using LSB steganography
✓ Users can download and decode images on any steganography tool

How to use:
1. Unzip and import into Eclipse/IntelliJ as an existing Maven project.
2. Build and run OnlineStegoApplication.
3. Open http://localhost:8081/register to create users (create at least two).
4. Login with one user, go to Home, choose a contact, send message by uploading an image and hiding text.
5. The encoded image is stored under the 'uploads' folder in project root (see application.properties app.upload.dir).
6. To decode a message:
   - Click 'Reveal Hidden Message' button on any message
   - OR upload any encoded image using the "Decode Any Image" section
7. Download images anytime using the download button

New Features:
- Download button for each message image
- Upload and decode any steganography image
- No message text stored in database (security)
- Improved UI with better error handling

Notes and limitations:
- This is a minimal example for learning. Production apps need more robust security, input validation, error handling, and better file/path handling.
- StegoUtil implements a simple LSB technique and expects available capacity. Use PNG images for reliable results.
- Message text is ONLY stored embedded in the image, never in the database or files.
