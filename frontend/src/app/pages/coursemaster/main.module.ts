import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UrlSettings} from 'src/app/global/url-settings';
import {MainComponent} from './main.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {FormsModule, ReactiveFormsModule} from '@angular/forms'
import {SharedModule} from '../shared/shared.module';
import {NgIf, NgStyle} from "@angular/common";
import {VideoService} from "./services/video.service";
import {MatSidenavModule} from "@angular/material/sidenav";
import {ThumbnailService} from "./services/thumbnail-service.service";
import {MatIconModule} from "@angular/material/icon";
import {UrlGuardService} from "../../services/url-guard.service";
import {MatMenuModule} from "@angular/material/menu";

const appRoutes: Routes = [
  {
    path: '', component: MainComponent, canActivate: [UrlGuardService], children: [
      { path: '', loadChildren: () => import('./viewer/viewer.module').then(m => m.ViewerModule)},
      { path: UrlSettings.INSTRUCTOR, canLoad: [UrlGuardService], loadChildren: () => import('./instructor/instructor.module').then(m => m.InstructorModule)}
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(appRoutes),
    MatToolbarModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    NgIf,
    MatSidenavModule,
    MatIconModule,
    NgStyle,
    MatMenuModule
  ],
  declarations: [
    MainComponent
  ],
  providers: [
    VideoService,
    ThumbnailService
  ]
})
export class MainModule {
}