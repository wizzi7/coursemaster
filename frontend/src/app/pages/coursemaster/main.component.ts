import {Component} from "@angular/core";
import {AuthService} from "../auth/login/services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UrlSettings} from "../../global/url-settings";
import {SearchService} from "../../services/search.service";
import {ERole} from "../auth/models/e-role";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-main',
  templateUrl: 'main.component.html',
  styleUrls: ['main.component.css']
})
export class MainComponent {
  loggedIn: boolean;
  searchBarVisible: boolean = true;
  isInstructor: boolean;
  public storage: Storage = localStorage;

  constructor(private authService: AuthService, private router: Router, private activatedRoute: ActivatedRoute,
              private searchService: SearchService, private userService: UserService) {
  }

  ngOnInit(): void {
    this.loggedIn = this.authService.loggedIn().valueOf();
    this.userService.getUserRole()?.then(role => this.isInstructor = (role == ERole.INSTRUCTOR || role == ERole.ADMIN));
    this.activatedRoute.url.subscribe(url => {
      this.searchBarVisible = this.router.url !== '/' + UrlSettings.INSTRUCTOR;
    });
  }

  logoutClicked() {
    this.authService.logout().subscribe(value => {
      this.storage.removeItem('access_token');
      this.storage.removeItem('refresh_token');
      this.storage.removeItem('current_user_role');
      this.loggedIn = false;
      this.router.navigate(['/']);
    });
  }

  navigateToHomePage() {
    this.router.navigateByUrl('/').then(() => {
      this.searchService.setSearchValue('');
      this.searchBarVisible = true;
      this.router.navigate(['/']);
    });
  }
}
