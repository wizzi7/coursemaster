import {Component, OnInit} from '@angular/core';
import {SearchService} from "../../../../services/search.service";
import {VideoService} from "../../services/video.service";
import {VideoDownloadDTO} from "../../instructor/models/video-download-dto";
import {ThumbnailService} from "../../services/thumbnail-service.service";
import {ThumbnailDTO} from "../../models/thumbnail-dto";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'video-list',
  templateUrl: './video-list.component.html',
  styleUrls: ['./video-list.component.css']
})
export class VideoListComponent implements OnInit {
  searchCriteria: string;
  dataSourceVideos: VideoDownloadDTO[];
  private storage: Storage = localStorage;

  constructor(private searchService: SearchService, private videoService: VideoService, private thumbnailService: ThumbnailService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.url.subscribe(url => {
       if (url[0] && url[0].path === 'result') {
        this.searchService.getSearchValue().subscribe(searchCriteria => {
          if (searchCriteria == '') {
            searchCriteria = this.storage.getItem('searchValue') ?? '';
          }

          this.loadVideosBySearchCriteria(searchCriteria);
        });
      } else {
        this.loadNewestVideos();
      }
    });
  }

  private loadNewestVideos() {
    this.videoService.getNewestVideos(0, 50).subscribe(videos => {
      this.dataSourceVideos = videos.content;
      this.uploadThumbnails();
    })
  }

  private loadVideosBySearchCriteria(searchCriteria: string) {
    this.videoService.getAllVideosByTitle(searchCriteria, 0 ,50).subscribe(videos => {
      this.dataSourceVideos = videos.content;
      this.uploadThumbnails();
    })
  }

  private uploadThumbnails() {
    this.dataSourceVideos.forEach(video => {
      if (video.thumbnailDTO.id) {
        this.thumbnailService.getThumbnailById(video.thumbnailDTO.id).subscribe(thumbnail => {
          video.thumbnailBase64Data = 'data:image' + this.getContentType(thumbnail) + ';base64, ' + thumbnail.data;
        })
      }
    })
  }

  private getContentType(thumbnail: ThumbnailDTO) {
    if (thumbnail && thumbnail.contentType && thumbnail.contentType.startsWith('image')) {
      return thumbnail.contentType.split('/')[1];
    } else return 'jpeg';
  }
}
