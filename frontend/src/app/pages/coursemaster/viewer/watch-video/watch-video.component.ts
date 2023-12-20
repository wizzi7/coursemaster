import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../../services/video.service";
import {VideoDownloadDTO} from "../../instructor/models/video-download-dto";
import {ThumbnailService} from "../../services/thumbnail-service.service";
import {ThumbnailDTO} from "../../models/thumbnail-dto";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'watch-video',
  templateUrl: './watch-video.component.html',
  styleUrls: ['./watch-video.component.css']
})
export class WatchVideoComponent implements OnInit {
  videoUUID: string;
  video: VideoDownloadDTO;
  videoSrc: any;

  constructor(private route: ActivatedRoute, private videoService: VideoService, private thumbnailService: ThumbnailService, private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => this.videoUUID = params['videoUUID']);
    this.videoService.getVideoDetails(this.videoUUID).subscribe(data => {
      this.video = data;
      this.loadThumbnail(this.video.thumbnailDTO.id);
      this.loadVideo(this.video.videoUUID);
    })
  }

  private loadThumbnail(id: number) {
    if (id) {
      this.thumbnailService.getThumbnailById(id).subscribe(thumbnail => {
        this.video.thumbnailBase64Data = 'data:image' + this.getContentType(thumbnail) + ';base64, ' + thumbnail.data;
      })
    }
  }

  private getContentType(thumbnail: ThumbnailDTO) {
    if (thumbnail && thumbnail.contentType && thumbnail.contentType.startsWith('image')) {
      return thumbnail.contentType.split('/')[1];
    } else return 'jpeg';
  }

  private loadVideo(videoUUID: string) {
    this.videoService.videoPlayback(videoUUID).subscribe(blob => {
      const videoUrl = URL.createObjectURL(blob);
      this.videoSrc = this.sanitizer.bypassSecurityTrustUrl(videoUrl);
    });
  }
}
