import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { SharedModule } from "../../shared/shared.module";
import { CommonModule } from "@angular/common";
import { RegisterFormComponent } from './register-form/register-form.component';
import { RegisterComponent } from "./register.component";
import {MatIconModule} from "@angular/material/icon";
import {MatSelectModule} from "@angular/material/select";

@NgModule({
  imports: [
    RouterModule.forChild([
      {path: '', component: RegisterComponent}
    ]),
    NgbModule,
    MatInputModule,
    FormsModule,
    SharedModule,
    ReactiveFormsModule,
    CommonModule,
    MatIconModule,
    MatSelectModule
  ],
    declarations: [
      RegisterComponent,
      RegisterFormComponent
    ],
    providers: []
  })
export class RegisterModule {

}