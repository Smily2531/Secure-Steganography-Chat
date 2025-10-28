# Button Fix - Reveal Hidden Message & Download Image

## Problem

The "Reveal Hidden Message" and "Download Image" buttons on the left side (for chat messages) were not working, even though the "Decode Message" button on the right side was working fine.

## Root Cause

The buttons were using Thymeleaf's `th:onclick` attribute with complex JavaScript string escaping:

```html
th:onclick="'decodeMsg(\\'' + ${m.imagePath} + '\\',' + ${m.id} + ');'"
```

This approach causes issues with:
- Special characters in file paths
- Quote escaping complexity
- Thymeleaf template rendering

## Solution

### 1. Changed to Data Attributes

Instead of inline onclick with complex escaping, we used HTML5 data attributes:

```html
<!-- BEFORE -->
<button th:onclick="'decodeMsg(\\'' + ${m.imagePath} + '\\',' + ${m.id} + ');'">

<!-- AFTER -->
<button class="decode-reveal-btn" 
        th:attr="data-image-path=${m.imagePath}, data-message-id=${m.id}">
```

### 2. Added Event Listeners

Added JavaScript event listeners that run when the page loads:

```javascript
document.addEventListener('DOMContentLoaded', function() {
  // Reveal Hidden Message buttons
  document.querySelectorAll('.decode-reveal-btn').forEach(function(btn) {
    btn.addEventListener('click', function() {
      const path = this.getAttribute('data-image-path');
      const id = this.getAttribute('data-message-id');
      decodeMsg(path, id);
    });
  });
  
  // Download buttons
  document.querySelectorAll('.download-btn').forEach(function(btn) {
    btn.addEventListener('click', function() {
      const path = this.getAttribute('data-image-path');
      const id = this.getAttribute('data-message-id');
      downloadImage(path, 'encoded-message-' + id + '.png');
    });
  });
});
```

### 3. Improved Error Handling

Enhanced the `decodeMsg()` function to handle missing elements gracefully:

```javascript
function decodeMsg(path, id) {
  const button = document.getElementById('decode-btn-' + id);
  const resultDiv = document.getElementById('decoded-' + id);
  
  if (!resultDiv) {
    console.error('Result div not found for id:', id);
    return;
  }
  
  if (button && button.disabled) return;
  
  // ... rest of the function
}
```

## Benefits

✅ **Cleaner HTML**: No complex string escaping  
✅ **More Reliable**: Data attributes are simpler and more reliable  
✅ **Better Debugging**: Console logs show exactly what's happening  
✅ **Easier Maintenance**: Standard JavaScript event listeners  

## Testing

### Test 1: Reveal Hidden Message
1. Login to the chat
2. See a message with an encoded image
3. Click "Reveal Hidden Message" button
4. ✅ Should show the hidden message

### Test 2: Download Image
1. In the same chat, click "Download Image" button
2. ✅ Image should download to your computer

### Test 3: Console Logging
1. Open browser console (F12)
2. Click the buttons
3. ✅ Should see: "Decoding path: /uploads/xxx.png Message ID: 1"

## Files Modified

- `src/main/resources/templates/chat.html`

## Why This Works Better

### Before (Problematic)
```html
th:onclick="'decodeMsg(\\'' + ${m.imagePath} + '\\',' + ${m.id} + ');'"
```

Issues:
- Complex escaping with `\\\'`
- Breaks with special characters in paths
- Hard to debug
- Template rendering issues

### After (Solution)
```html
th:attr="data-image-path=${m.imagePath}, data-message-id=${m.id}"
```

Benefits:
- Clean data attributes
- No escaping needed
- Works with any characters
- Easy to read and debug

## Status

✅ **FIXED** - Both buttons now work correctly!

