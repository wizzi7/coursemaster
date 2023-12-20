import {Injectable} from "@angular/core";
import {AuthenticationResponse} from "../pages/auth/models/authentication-response";

@Injectable()
export class TokenService {
  public storage: Storage = localStorage;

  constructor() {}

  storeTokenInfo(token: AuthenticationResponse) {
    this.storage.setItem('access_token', token.accessToken);
    this.storage.setItem('refresh_token', token.refreshToken);
    location.replace('http://localhost:4200')
  }

  getAccessToken() {
    return this.storage.getItem('access_token');
  }

  getRefreshToken() {
    return this.storage.getItem('refresh_token');
  }

  removeAccessToken() {
    this.storage.removeItem('access_token');
  }
}
