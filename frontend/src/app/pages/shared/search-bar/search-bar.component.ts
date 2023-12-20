import {Component, OnInit} from '@angular/core';
import {SearchService} from "../../../services/search.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {
  form: FormGroup;
  private storage: Storage = localStorage;

  constructor(private searchService: SearchService, private router: Router) {
  }

  ngOnInit() {
    this.createForm();
  }

  private createForm() {
    this.form = new FormGroup({
      searchInput: new FormControl(this.storage.getItem('searchValue') ?? '', [Validators.required, Validators.minLength(1)]),
    });
  }

  onSearchClick() {
    this.searchService.setSearchValue(this.form.controls['searchInput'].value);
    this.router.navigate(['/result']);
  }
}
