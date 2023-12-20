import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { LoginFormComponent } from "./login-form/login-form.component";
import { LoginComponent } from "./login.component";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { SharedModule } from "../../shared/shared.module";
import { CommonModule } from "@angular/common";
import {MatIconModule} from "@angular/material/icon";
import {UserService} from "../../../services/user.service";

@NgModule({
  imports: [
    RouterModule.forChild([
      {path: '', component: LoginComponent}
    ]),
    NgbModule,
    MatInputModule,
    FormsModule,
    SharedModule,
    ReactiveFormsModule,
    CommonModule,
    MatIconModule
  ],
    declarations: [
      LoginComponent,
      LoginFormComponent
    ],
    providers: [
      UserService]
  })
export class LoginModule {

}