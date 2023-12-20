import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {ApiSettings} from "../../../global/api-settings";
import {ThumbnailDTO} from "../models/thumbnail-dto";

@Injectable()
export class ThumbnailService {

  constructor(private http: HttpClient) {
  }
  getThumbnailById(id: number) {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<ThumbnailDTO>(ApiSettings.COURSE_SERVICE_THUMBNAILS_GET, {params: params})
  }
}
