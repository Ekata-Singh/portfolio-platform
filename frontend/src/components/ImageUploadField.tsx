import { useRef, useState, type ClipboardEvent, type DragEvent } from 'react';

interface ImageUploadFieldProps {
  label: string;
  previewUrl?: string | null;
  uploading?: boolean;
  disabled?: boolean;
  disabledMessage?: string;
  onFileSelected: (file: File) => void;
}

export function ImageUploadField({
  label,
  previewUrl,
  uploading = false,
  disabled = false,
  disabledMessage = 'Save first to add an image.',
  onFileSelected,
}: ImageUploadFieldProps) {
  const inputRef = useRef<HTMLInputElement>(null);
  const [isDragging, setIsDragging] = useState(false);
  const [localPreview, setLocalPreview] = useState<string | null>(null);

  const handleFile = (file: File | null | undefined) => {
    if (!file || !file.type.startsWith('image/')) {
      return;
    }

    setLocalPreview(URL.createObjectURL(file));
    onFileSelected(file);
  };

  const handleDrop = (event: DragEvent<HTMLDivElement>) => {
    event.preventDefault();
    setIsDragging(false);
    handleFile(event.dataTransfer.files[0]);
  };

  const handlePaste = (event: ClipboardEvent<HTMLDivElement>) => {
    const item = Array.from(event.clipboardData.items).find((entry) =>
      entry.type.startsWith('image/'),
    );
    handleFile(item?.getAsFile());
  };

  const displayUrl = localPreview ?? previewUrl;

  return (
    <div className="image-upload-field">
      <span className="image-upload-label">{label}</span>

      <div
        className={[
          'image-dropzone',
          isDragging ? 'dragging' : '',
          disabled ? 'disabled' : '',
        ]
          .filter(Boolean)
          .join(' ')}
        onClick={() => !disabled && inputRef.current?.click()}
        onKeyDown={(e) => {
          if (!disabled && (e.key === 'Enter' || e.key === ' ')) {
            inputRef.current?.click();
          }
        }}
        onDragOver={(e) => {
          e.preventDefault();
          if (!disabled) {
            setIsDragging(true);
          }
        }}
        onDragLeave={() => setIsDragging(false)}
        onDrop={disabled ? undefined : handleDrop}
        onPaste={disabled ? undefined : handlePaste}
        tabIndex={disabled ? -1 : 0}
        role="button"
        aria-label={label}
      >
        {displayUrl ? (
          <img src={displayUrl} alt="Preview" className="image-dropzone-preview" />
        ) : (
          <p className="image-dropzone-placeholder">
            {disabled ? disabledMessage : 'Click to browse, drag & drop, or paste an image'}
          </p>
        )}

        {uploading && <div className="image-dropzone-overlay">Uploading...</div>}
      </div>

      <input
        ref={inputRef}
        type="file"
        accept="image/*"
        hidden
        onChange={(e) => handleFile(e.target.files?.[0])}
      />
    </div>
  );
}
