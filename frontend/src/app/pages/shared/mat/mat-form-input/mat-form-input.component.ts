import { Component, Input } from "@angular/core";
import { FormGroup } from "@angular/forms";

@Component({
    selector: 'mat-form-input',
    templateUrl: './mat-form-input.component.html',
    styleUrls: []
})
export class MaterialFormInputComponent {
    @Input() form: FormGroup;
    @Input() field: string;
    @Input() placeholder: string;
    @Input() whenDirty: boolean = false;
    @Input() fieldId: string;
    @Input() label: string;
    @Input() isPassword: boolean = false;
}