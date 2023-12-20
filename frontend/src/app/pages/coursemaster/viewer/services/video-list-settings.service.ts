import {Injectable} from "@angular/core";
import {VideoListSettings} from "../models/video-list-settings";

@Injectable()
export class InstructorVideoListSettingsService {
  private videoListSettings = "videoListSettings";
  private storage: Storage = localStorage;

  storeVideoListSettings(videoListSettings: VideoListSettings) {
    this.storage.setItem(this.videoListSettings, JSON.stringify(videoListSettings));
  }

  getVideoListSettings(): VideoListSettings {
    let item = this.storage.getItem(this.videoListSettings);
    return item ? JSON.parse(item) : new VideoListSettings(0, 50);
  }
}
