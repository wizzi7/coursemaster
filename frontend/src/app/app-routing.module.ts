import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UrlSettings } from './global/url-settings';

const routes: Routes = [
{
  path: UrlSettings.MAIN,
  loadChildren: () => import('./pages/coursemaster/main.module').then(m => m.MainModule)
},
{
  path: UrlSettings.LOGIN,
  loadChildren: () => import('./pages/auth/login/login.module').then(m => m.LoginModule)
},
{
  path: UrlSettings.REGISTER,
  loadChildren: () => import('./pages/auth/register/register.module').then(m => m.RegisterModule)
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
