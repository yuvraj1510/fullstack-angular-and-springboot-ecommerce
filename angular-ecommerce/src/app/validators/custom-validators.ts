import { FormControl, ValidationErrors } from "@angular/forms";

export class CustomValidators {

    static notOnlyWhileSpace(control: FormControl) : ValidationErrors {
        if((control.value != null) && (control.value.trim().length === 0)) {
            return { 'notOnlyWhileSpace': true }
        } else {
            return null as any;
        }
    }
}
