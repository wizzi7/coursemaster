import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {VideoListComponent} from './video-list/video-list.component';
import {RouterModule, Routes} from "@angular/router";
import {MatIconModule} from "@angular/material/icon";
import {VideoTileComponent} from './video-list/video-tile/video-tile.component';
import {WatchVideoComponent} from './watch-video/watch-video.component';
import {UrlSettings} from "../../../global/url-settings";
import {UrlGuardService} from "../../../services/url-guard.service";

const appRoutes: Routes = [{
  path: '', children: [
    {path: '', component: VideoListComponent, canActivate: [UrlGuardService]},
    {path: 'result', component: VideoListComponent, canActivate: [UrlGuardService]},
    {path: UrlSettings.WATCH + '/:videoUUID', component: WatchVideoComponent, canActivate: [UrlGuardService]}
  ]

}]

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(appRoutes),
    MatIconModule,
  ],
  declarations: [
    VideoListComponent,
    VideoTileComponent,
    WatchVideoComponent
  ]
})
export class ViewerModule {
}
