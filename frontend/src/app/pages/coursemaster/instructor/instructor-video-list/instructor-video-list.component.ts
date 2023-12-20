import {Component, OnInit} from '@angular/core';
import {VideoService} from "../../services/video.service";
import {MatTableDataSource} from "@angular/material/table";
import {VideoViewSettings} from "../models/video-view-settings";
import {InstructorVideoListSettingsService} from "../services/instructor-video-list-settings.service";
import {PageEvent} from "@angular/material/paginator";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {AddVideoDialogComponent} from "../add-video-dialog/add-video-dialog.component";
import {ThumbnailService} from "../../services/thumbnail-service.service";
import {ThumbnailDTO} from "../../models/thumbnail-dto";
import {VideoDownloadDTO} from "../models/video-download-dto";

@Component({
  selector: 'instructor-video-list',
  templateUrl: './instructor-video-list.component.html',
  styleUrls: ['./instructor-video-list.component.css']
})
export class InstructorVideoListComponent implements OnInit {
  dataSource: MatTableDataSource<VideoDownloadDTO> = new MatTableDataSource();
  displayColumns: string[] = ["thumbnail", "title", "description", "uploadDate", "options"];
  addVideoDialog: MatDialogRef<AddVideoDialogComponent>;
  viewSettings: VideoViewSettings;
  resultSize: number;

  constructor(private videoService: VideoService, private videoViewSettingsService: InstructorVideoListSettingsService,
              private dialog: MatDialog, private thumbnailService: ThumbnailService) {
  }

  ngOnInit(): void {
    this.viewSettings = this.videoViewSettingsService.getVideoViewSettings();
    this.loadTable(this.viewSettings.page, this.viewSettings.pageSize);
  }

  private loadTable(pageIndex: number, pageSize: number) {
    this.videoService.getAllInstructorVideos(pageIndex, pageSize).subscribe(data => {
      this.dataSource.data = data.content;
      this.resultSize = data.totalElements;
      this.uploadThumbnails();
    })
  }

  pageChanged(pageEvent: PageEvent) {
    this.viewSettings.pageSize = pageEvent.pageSize;
    this.viewChange(this.viewSettings);
    this.loadTable(pageEvent.pageIndex, pageEvent.pageSize);
  }

  viewChange(event: VideoViewSettings) {
    this.videoViewSettingsService.storeVideoViewSettings(event);
  }

  addVideo() {
    this.addVideoDialog = this.dialog.open(AddVideoDialogComponent, {});

    const saveSub = this.addVideoDialog.componentInstance.onSave.subscribe(() => {
      this.loadTable(this.viewSettings.page, this.viewSettings.pageSize);
    })

    this.addVideoDialog.afterClosed().subscribe(() => saveSub.unsubscribe());
  }

  private uploadThumbnails() {
    this.dataSource.data.forEach(video => {
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

  deleteVideo(id: number) {
    this.videoService.delete(id).subscribe(deleted => {
      this.loadTable(this.viewSettings.page, this.viewSettings.pageSize);
    })
  }
}
