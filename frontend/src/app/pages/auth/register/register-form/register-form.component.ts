import {Component, OnInit} from '@angular/core';
import {FormGroup, FormControl, Validators} from "@angular/forms";
import {RegisterService} from '../services/register.service';
import {ERole} from "../../models/e-role";
import {UserRegistrationDTO} from "../../models/user-registration-dto";
import {TokenService} from "../../../../services/token.service";
import {AppSelect} from "../../../shared/models/app-select";
import {UserService} from "../../../../services/user.service";


@Component({
  selector: 'register-form',
  templateUrl: 'register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent implements OnInit {
  formGroup: FormGroup;
  isDirty = true;
  model: UserRegistrationDTO;
  roles: AppSelect[];
  selectedRoleOption: string;

  constructor(private registerService: RegisterService, private tokenService: TokenService, private userService: UserService) {
  }

  ngOnInit() {
    this.loadRoles();
    this.createFormGroup();
  }

  private createFormGroup() {
    this.formGroup = new FormGroup({
      firstname: new FormControl('', [Validators.required, Validators.minLength(3)]),
      lastname: new FormControl('', [Validators.required, Validators.minLength(3)]),
      password: new FormControl('', [Validators.required, Validators.minLength(7)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      role: new FormControl('', [Validators.required])
    })
  }

  registerButtonClicked() {
    this.isDirty = false;
    if (this.formGroup.valid) {
      this.model = this.formGroup.value;
      this.registerService.register(this.model).subscribe(authDetails => {
        if (authDetails && authDetails.accessToken && authDetails.refreshToken) {
          this.tokenService.storeTokenInfo(authDetails);
          this.userService.storeUserInfo(authDetails);
        }
      })
    }
  }

  loadRoles() {
    this.roles = [new AppSelect(ERole.USER.toString(), ERole.USER.toString()), new AppSelect(ERole.INSTRUCTOR.toString(), ERole.INSTRUCTOR.toString())]
  }

  roleClick(event: any) {
    this.selectedRoleOption = event;
    this.formGroup.controls['role'].setValue(event);
  }
}