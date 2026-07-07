import { useRef, useState, type DragEvent } from 'react';

interface FileUploadFieldProps {
  label: string;
  accept: string;
  currentUrl?: string | null;
  uploading?: boolean;
  disabled?: boolean;
  disabledMessage?: string;
  onFileSelected: (file: File) => void;
}

export function FileUploadField({
  label,
  accept,
  currentUrl,
  uploading = false,
  disabled = false,
  disabledMessage = 'Save first to add a file.',
  onFileSelected,
}: FileUploadFieldProps) {
  const inputRef = useRef<HTMLInputElement>(null);
  const [isDragging, setIsDragging] = useState(false);
  const [selectedName, setSelectedName] = useState<string | null>(null);

  const handleFile = (file: File | null | undefined) => {
    if (!file) {
      return;
    }

    setSelectedName(file.name);
    onFileSelected(file);
  };

  const handleDrop = (event: DragEvent<HTMLDivElement>) => {
    event.preventDefault();
    setIsDragging(false);
    handleFile(event.dataTransfer.files[0]);
  };

  return (
    <div className="file-upload-field">
      <span className="image-upload-label">{label}</span>

      <div
        className={['file-dropzone', isDragging ? 'dragging' : '', disabled ? 'disabled' : '']
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
        tabIndex={disabled ? -1 : 0}
        role="button"
        aria-label={label}
      >
        <p className="file-dropzone-text">
          {disabled
            ? disabledMessage
            : selectedName
              ? `Selected: ${selectedName}`
              : 'Click to browse or drag & drop a file'}
        </p>

        {uploading && <div className="image-dropzone-overlay">Uploading...</div>}
      </div>

      {currentUrl && !selectedName && (
        <a href={currentUrl} target="_blank" rel="noreferrer" className="file-current-link">
          View current file
        </a>
      )}

      <input
        ref={inputRef}
        type="file"
        accept={accept}
        hidden
        onChange={(e) => handleFile(e.target.files?.[0])}
      />
    </div>
  );
}
