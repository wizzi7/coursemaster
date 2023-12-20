import {Component, ElementRef, EventEmitter, Inject, OnInit, Output, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {VideoUploadDto} from "../models/video-upload-dto";
import {VideoService} from "../../services/video.service";

@Component({
  selector: 'add-video-dialog',
  templateUrl: './add-video-dialog.component.html',
  styleUrls: ['./add-video-dialog.component.css']
})
export class AddVideoDialogComponent implements OnInit {
  uploadView: boolean = true;
  uploadedThumbnail: boolean = false;
  formGroup: FormGroup;
  videoUploadDTO: VideoUploadDto;
  thumbnailBase64Data: string;
  @ViewChild('inputFile') inputFile: ElementRef;
  @ViewChild('inputThumbnail') inputThumbnail: ElementRef;
  @Output() onSave = new EventEmitter();

  constructor(private dialogRef: MatDialogRef<AddVideoDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any,
              private videoService: VideoService) { }

  ngOnInit(): void {
    this.createForm();
  }

  private createForm() {
    this.formGroup = new FormGroup({
      title: new FormControl('', [Validators.required, Validators.minLength(3)]),
      description: new FormControl('', [Validators.required]),
      video: new FormControl('', [Validators.required]),
      thumbnail: new FormControl('', [Validators.required])
    })
  }

  closeDialog() {
    this.dialogRef.close();
  }

  onVideoSelected(event: any) {
    if (event && event.target && event.target.files) {
      this.formGroup.controls['video'].setValue(event.target.files[0]);
      this.uploadView = false;
    }
  }

  onThumbnailSelected(event: any) {
    if (event && event.target && event.target.files) {
      this.formGroup.controls['thumbnail'].setValue(event.target.files[0]);
      this.uploadedThumbnail = this.convertToBase64(event.target.files[0]);
    }
  }

  private convertToBase64(file: any) {
    const reader = new FileReader();

    reader.onload = (e: ProgressEvent) => {
      this.thumbnailBase64Data = (e.target as FileReader).result as string;
    };

    reader.readAsDataURL(file);
    return true;
  }

  private getContentType(contentType: string) {
    if (contentType && contentType.startsWith('image')) {
      return contentType.split('/')[1];
    } else return 'jpeg';
  }

  inputFileClicked() {
    this.inputFile.nativeElement.click();
  }

  inputThumbnailClicked() {
    this.inputThumbnail.nativeElement.click();
  }

  cancelClicked() {
    this.dialogRef.close();
  }

  saveClicked() {
    if (this.formGroup.valid) {
      this.videoUploadDTO = this.formGroup.value;
      this.videoService.save(this.videoUploadDTO.title, this.videoUploadDTO.description, this.videoUploadDTO.video, this.videoUploadDTO.thumbnail).subscribe(result => {
        if (result) {
          this.onSave.emit();
          this.dialogRef.close();
        }
      })
    }
  }
}
