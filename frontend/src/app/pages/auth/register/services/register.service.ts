import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {UserRegistrationDTO} from "../../models/user-registration-dto";
import {ApiSettings} from "src/app/global/api-settings";
import {AuthenticationResponse} from "../../models/authentication-response";


@Injectable()
export class RegisterService {

  constructor(private http: HttpClient) {
  }

  register(user: UserRegistrationDTO) {
    return this.http.post<AuthenticationResponse>(ApiSettings.AUTH_SERVICE_REGISTER, user);
  }
}