import {Component} from "@angular/core";
import {FormGroup, FormControl, Validators} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {AuthenticationRequest} from "../models/authentication-request";
import {TokenService} from "../../../../services/token.service";
import {UserService} from "../../../../services/user.service";

@Component({
  selector: 'login-form',
  templateUrl: 'login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {
  formGroup: FormGroup;
  isDirty = true;
  authenticationRequest: AuthenticationRequest;
  loginFailed: boolean = false;

  constructor(private authService: AuthService, private tokenService: TokenService,
              private userService: UserService) {
  }

  ngOnInit() {
    this.createFormGroup();
  }

  private createFormGroup() {
    this.formGroup = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(7)]),
    })
  }

  loginButtonClicked() {
    if (this.formGroup.valid) {
      this.authenticationRequest = this.formGroup.value;
      this.authService.authenticate(this.authenticationRequest).subscribe(authDetails => {
        if (authDetails && authDetails.accessToken && authDetails.refreshToken) {
          this.tokenService.storeTokenInfo(authDetails);
          this.userService.storeUserInfo(authDetails);
          this.loginFailed = false;
        } else
          this.loginFailed = true;
      }, error => {
        this.loginFailed = true;
      })
    }
  }
}