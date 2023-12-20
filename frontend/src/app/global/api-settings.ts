export class ApiSettings {
  public static URL = 'http://localhost:8080/api';
  public static API_URL = ApiSettings.URL + '/v1';

  //AUTH_SERVICE
  public static AUTH_SERVICE= ApiSettings.API_URL + '/uaa/auth';
  public static AUTH_SERVICE_REGISTER = ApiSettings.AUTH_SERVICE + '/register';
  public static AUTH_SERVICE_AUTHENTICATE = ApiSettings.AUTH_SERVICE + '/authenticate';
  public static AUTH_SERVICE_REFRESH_TOKEN = ApiSettings.AUTH_SERVICE + '/refreshToken';
  public static AUTH_SERVICE_LOGOUT = ApiSettings.AUTH_SERVICE + '/logout';

  //COURSE_SERVICE
  public static COURSE_SERVICE_VIDEOS = ApiSettings.API_URL + '/courses/videos';
  public static COURSE_SERVICE_VIDEOS_SAVE = ApiSettings.COURSE_SERVICE_VIDEOS + '/save';
  public static COURSE_SERVICE_VIDEOS_DELETE = ApiSettings.COURSE_SERVICE_VIDEOS + '/deleteById';
  public static COURSE_SERVICE_VIDEOS_GET_ALL_FOR_INSTRUCTOR = ApiSettings.COURSE_SERVICE_VIDEOS + '/getAllInstructorVideos';
  public static COURSE_SERVICE_VIDEOS_GET_ALL_VIDEOS_BY_TITLE = ApiSettings.COURSE_SERVICE_VIDEOS + '/getAllVideosByTitle';
  public static COURSE_SERVICE_VIDEOS_GET_VIDEO_DETAILS = ApiSettings.COURSE_SERVICE_VIDEOS + '/getVideoDetails';
  public static COURSE_SERVICE_VIDEOS_VIDEO_PLAYBACK = ApiSettings.COURSE_SERVICE_VIDEOS + '/videoPlayback';
  public static COURSE_SERVICE_VIDEOS_GET_NEWEST_VIDEOS = ApiSettings.COURSE_SERVICE_VIDEOS + '/getNewestVideos';

  public static COURSE_SERVICE_THUMBNAILS = ApiSettings.API_URL + '/courses/thumbnails';
  public static COURSE_SERVICE_THUMBNAILS_GET = ApiSettings.COURSE_SERVICE_THUMBNAILS + '/getById';
}
