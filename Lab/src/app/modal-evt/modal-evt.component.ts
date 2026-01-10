import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-modal-evt',
  templateUrl: './modal-evt.component.html',
  styleUrls: ['./modal-evt.component.css']
})
export class ModalEvtComponent {
   form!: FormGroup;
    constructor( public dialogRef: MatDialogRef<ModalEvtComponent>) {
      this.form=new FormGroup({
        titre:new FormControl (null),
        dateDebut:new FormControl (null),
        dateFin:new FormControl (null),
        lieu: new FormControl(null)

      })
    }
  

   save() {
        this.dialogRef.close(this.form.value);
    }

    close() {
        this.dialogRef.close();
    }

}
