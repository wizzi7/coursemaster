export class AppSelect {

  value: any;
  display: string;
  selected: boolean = false;
  constructor(value: any, display: string) {
    this.display = display;
    this.value = value;
  }
}