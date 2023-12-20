import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AppSelect} from "../../models/app-select";
import {MatSelectChange} from "@angular/material/select";

@Component({
  selector: 'material-select',
  templateUrl: './material-select.component.html'
})
export class MaterialSelectComponent implements OnInit {
  @Input() id: string;
  @Input() items: AppSelect[];
  @Input() label: string;
  @Input() selected: any;
  @Input() required: boolean;
  @Output() elementClick = new EventEmitter<any>();
  selectedElement: string;

  constructor() {
  }

  ngOnInit(): void {
  }

  elementClicked(event: MatSelectChange) {
    this.selectedElement = event.value;
    this.elementClick.emit(event.value);
  }

}
