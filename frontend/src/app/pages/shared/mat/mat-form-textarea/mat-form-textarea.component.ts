import {Component, Input} from '@angular/core';
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'mat-form-textarea',
  templateUrl: './mat-form-textarea.component.html',
  styleUrls: ['./mat-form-textarea.component.css']
})
export class MatFormTextareaComponent {
  @Input() form: FormGroup;
  @Input() field: string;
  @Input() placeholder: string;
  @Input() whenDirty: boolean = false;
  @Input() fieldId: string;
  @Input() label: string;
}
