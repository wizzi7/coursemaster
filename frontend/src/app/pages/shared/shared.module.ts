import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MaterialFormInputComponent} from "./mat/mat-form-input/mat-form-input.component";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MaterialSelectComponent} from './mat/material-select/material-select.component'
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {MatFormTextareaComponent} from './mat/mat-form-textarea/mat-form-textarea.component';
import {SearchBarComponent} from './search-bar/search-bar.component';
import {MatIconModule} from "@angular/material/icon";

@NgModule({
  imports: [
    CommonModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    MatOptionModule,
    MatSelectModule,
    MatIconModule
  ],
  declarations: [
    MaterialFormInputComponent,
    MaterialSelectComponent,
    MatFormTextareaComponent,
    SearchBarComponent
  ],
  exports: [
    MaterialFormInputComponent,
    MatButtonModule,
    MaterialSelectComponent,
    MatFormTextareaComponent,
    SearchBarComponent
  ],
  providers: []
})

export class SharedModule {

}