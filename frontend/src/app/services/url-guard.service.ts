import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Route, Router, RouterStateSnapshot, UrlSegment} from "@angular/router";
import {UrlSettings} from "../global/url-settings";
import {UserService} from "./user.service";
import {ERole} from "../pages/auth/models/e-role";

@Injectable()
export class UrlGuardService {

  constructor(private userService: UserService, private router: Router) {
  }

  canLoad() {
    const navigation = this.router.getCurrentNavigation();
    let url = '/';

    if (navigation)
      url = navigation.extractedUrl.toString();

    //return this.checkPermission(url);
    return Promise.resolve(this.checkPermission(url)).then(accessGranted => {
      if (!accessGranted) {
        this.router.navigate(['/']);
        return false;
      }
      return true;
    });
  }

  canActivate(state: RouterStateSnapshot) {
    return Promise.resolve(this.checkPermission(state.url)).then(accessGranted => {
      if (!accessGranted) {
        this.router.navigate(['/']);
        return false;
      }
      return true;
    });
  }

  private checkPermission(url: string) {
    let pattern = '/';

    if (url === pattern + UrlSettings.INSTRUCTOR)
      return this.userService.userHasAuthority(ERole.INSTRUCTOR);

    return true;
  }
}