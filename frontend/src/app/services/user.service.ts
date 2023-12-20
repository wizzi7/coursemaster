import {Injectable} from "@angular/core";
import {AuthenticationResponse} from "../pages/auth/models/authentication-response";
import {CryptoService} from "./crypto.service";
import {ERole} from "../pages/auth/models/e-role";

@Injectable()
export class UserService {
  public storage: Storage = localStorage;
  private currentUserRole = 'current_user_role';

  constructor(private cryptoService: CryptoService) {}

  storeUserInfo(authDetails: AuthenticationResponse) {
    this.cryptoService.encrypt(authDetails.role).then(encrypted => this.storage.setItem('current_user_role', encrypted));
  }

  getUserRole() {
    try {
      let userInfo = this.storage.getItem(this.currentUserRole);
      if (userInfo) {
        return this.cryptoService.decrypt(userInfo);
      }
      return null;
    } catch (e) {
      return null;
    }
  }

  userHasAuthority(role: ERole) {
    return this.getUserRole()?.then(userRole => {
      if (userRole) {
        if (userRole === ERole.ADMIN || role.toString() == userRole)
          return true;
      }
      return false;
    }) ?? false;
  }
}