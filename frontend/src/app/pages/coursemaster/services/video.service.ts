import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {ApiSettings} from "../../../global/api-settings";
import {PageResult} from "../../shared/models/page-result";
import {VideoDownloadDTO} from "../instructor/models/video-download-dto";
import {Observable} from "rxjs";
@Injectable()
export class VideoService {

  constructor(private http: HttpClient) {
  }

  save(title: string, description: string, video: File, thumbnail: string) {
    const formData = new FormData();
    formData.append('title', title);
    formData.append('description', description);
    formData.append('video', video);
    formData.append('thumbnail', thumbnail);
    return this.http.post(ApiSettings.COURSE_SERVICE_VIDEOS_SAVE, formData);
  }

  getAllInstructorVideos(page: number, size: number) {
    let params = new HttpParams().set('page', page).set('size', size);//.set('sort', )
    return this.http.get<PageResult<VideoDownloadDTO>>(ApiSettings.COURSE_SERVICE_VIDEOS_GET_ALL_FOR_INSTRUCTOR, {params: params});
  }

  delete(id: number) {
    return this.http.delete<boolean>(ApiSettings.COURSE_SERVICE_VIDEOS_DELETE + '/' + id);
  }

  getAllVideosByTitle(searchCriteria: string, page: number, size: number) {
    let params = new HttpParams().set('searchCriteria', searchCriteria).set('page', page).set('size', size);//.set('sort', )
    return this.http.get<PageResult<VideoDownloadDTO>>(ApiSettings.COURSE_SERVICE_VIDEOS_GET_ALL_VIDEOS_BY_TITLE, {params: params});
  }

  getVideoDetails(videoUUID: string) {
    return this.http.get<VideoDownloadDTO>(ApiSettings.COURSE_SERVICE_VIDEOS_GET_VIDEO_DETAILS + '/' + videoUUID);
  }

  videoPlayback(videoUUID: string): Observable<Blob> {
    return this.http.get<Blob>(ApiSettings.COURSE_SERVICE_VIDEOS_VIDEO_PLAYBACK + '/' + videoUUID, { responseType: 'blob' as 'json' });
  }

  getNewestVideos(page: number, size: number)  {
    let params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<PageResult<VideoDownloadDTO>>(ApiSettings.COURSE_SERVICE_VIDEOS_GET_NEWEST_VIDEOS, {params: params});
  }
}
