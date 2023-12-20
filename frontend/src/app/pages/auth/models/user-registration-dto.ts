import {ERole} from "./e-role";

export class UserRegistrationDTO {
  firstname: string;
  lastname: string;
  email: string;
  password: string;
  role: ERole;
}