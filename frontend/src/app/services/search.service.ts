import {Injectable} from "@angular/core";
import {BehaviorSubject, Observable} from "rxjs";

@Injectable()
export class SearchService {
  private searchSubject = new BehaviorSubject<string>('');
  private storage: Storage = localStorage;

  constructor() {
  }

  setSearchValue(value: string): void {
    this.searchSubject.next(value);
    this.storage.setItem('searchValue', value);
  }

  getSearchValue(): Observable<string> {
    return this.searchSubject.asObservable();
  }
}
