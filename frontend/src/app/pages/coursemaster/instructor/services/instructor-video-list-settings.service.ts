import {Injectable} from "@angular/core";
import {VideoViewSettings} from "../models/video-view-settings";

@Injectable()
export class InstructorVideoListSettingsService {
  private videoViewSettings = "videoViewSettings";
  private storage: Storage = localStorage;

  storeVideoViewSettings(videoViewSettings: VideoViewSettings) {
    this.storage.setItem(this.videoViewSettings, JSON.stringify(videoViewSettings));
  }

  getVideoViewSettings(): VideoViewSettings {
    let item = this.storage.getItem(this.videoViewSettings);
    return item ? JSON.parse(item) : new VideoViewSettings(0, 5);
  }
}
