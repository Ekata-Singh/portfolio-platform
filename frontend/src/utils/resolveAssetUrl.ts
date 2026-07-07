import { API_ORIGIN } from '../config';

/**
 * Uploaded files are stored server-side as a relative path (e.g. "profile/123_photo.jpg")
 * served from /uploads/**, while manually-entered values may already be a full http(s) URL.
 */
export function resolveAssetUrl(path?: string | null): string | null {
  if (!path) {
    return null;
  }

  if (/^https?:\/\//i.test(path)) {
    return path;
  }

  return `${API_ORIGIN}/uploads/${path}`;
}
