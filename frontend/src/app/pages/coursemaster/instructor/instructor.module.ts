import {NgModule} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {InstructorVideoListComponent} from './instructor-video-list/instructor-video-list.component';
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {AddVideoDialogComponent} from './add-video-dialog/add-video-dialog.component';
import {MatButtonModule} from "@angular/material/button";
import {MatDialogModule} from "@angular/material/dialog";
import {MatIconModule} from "@angular/material/icon";
import {SharedModule} from "../../shared/shared.module";
import {MatMenuModule} from "@angular/material/menu";
import {InstructorVideoListSettingsService} from "./services/instructor-video-list-settings.service";
import {UrlGuardService} from "../../../services/url-guard.service";

const appRoutes: Routes = [{
  path: '', component: InstructorVideoListComponent, canActivate: [UrlGuardService]

}]

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(appRoutes),
    MatTableModule,
    MatPaginatorModule,
    MatButtonModule,
    MatDialogModule,
    MatIconModule,
    SharedModule,
    MatMenuModule,
    NgOptimizedImage
  ],
  declarations: [
    InstructorVideoListComponent,
    AddVideoDialogComponent
  ],
  providers: [
    InstructorVideoListSettingsService
  ]
})
export class InstructorModule {
}
