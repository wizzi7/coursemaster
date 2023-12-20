import {ThumbnailDTO} from "../../models/thumbnail-dto";

export class VideoDownloadDTO {
  id: number;
  title: string;
  description: string;
  fileName: string;
  uploadDate: any;
  thumbnailBase64Data: string;
  thumbnailDTO: ThumbnailDTO;
  creatorFullName: string;
  videoUUID: string;
}
