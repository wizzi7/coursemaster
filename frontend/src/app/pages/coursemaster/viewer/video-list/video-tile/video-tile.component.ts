import {Component, Input, OnInit} from '@angular/core';
import {VideoDownloadDTO} from "../../../instructor/models/video-download-dto";
import {Router} from "@angular/router";

@Component({
  selector: 'video-tile',
  templateUrl: './video-tile.component.html',
  styleUrls: ['./video-tile.component.css']
})
export class VideoTileComponent implements OnInit {
  @Input() video: VideoDownloadDTO;

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  onImageClick() {
    this.router.navigate(['/watch/' + this.video.videoUUID]);
  }
}
