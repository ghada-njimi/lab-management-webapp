import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Title } from '@angular/platform-browser';
import { EvtService } from 'src/Services/evt.service';
import { Evt } from 'src/Models/Evt';

@Component({
  selector: 'app-modal-evt',
  templateUrl: './modal-evt.component.html',
  styleUrls: ['./modal-evt.component.css']
})
export class ModalEvtComponent implements OnInit {
   form!: FormGroup;
   isEditMode = false;
   eventId: number | null = null;

    constructor(
      public dialogRef: MatDialogRef<ModalEvtComponent>,
      @Inject(MAT_DIALOG_DATA) public data: number,
      private evtService: EvtService
    ) {
      this.form=new FormGroup({
        titre:new FormControl (null),
        dateDebut:new FormControl (null),
        dateFin:new FormControl (null),
        lieu: new FormControl(null)

      })
    }

    ngOnInit(): void {
      if (this.data) {
        this.isEditMode = true;
        this.eventId = this.data;
        this.evtService.getEvenementById(String(this.data)).subscribe({
          next: (evt: Evt) => {
            this.form.patchValue({
              titre: evt.titre,
              dateDebut: this.parseDate(evt.date),
              dateFin: this.parseDate(evt.date),
              lieu: evt.lieu
            });
          },
          error: (err) => {
            console.error('Erreur chargement événement:', err);
          }
        });
      }
    }
  

   save() {
        const value: any = { ...this.form.value };
        // If dateDebut provided (date range UI), convert to backend `date` (yyyy-MM-dd)
        if (value.dateDebut) {
          const d = toBackendDateLocal(value.dateDebut);
          if (d) {
            value.date = d;
          }
        }
        // remove range fields to match backend model
        delete value.dateDebut;
        delete value.dateFin;
        this.dialogRef.close(value);
    }

    close() {
        this.dialogRef.close();
    }

    private parseDate(value: any): Date | null {
      if (!value) return null;
      if (value instanceof Date) return value;
      if (typeof value === 'string') {
        const parts = value.split('-');
        if (parts.length === 3 && parts[0].length === 4) {
          const y = Number(parts[0]);
          const m = Number(parts[1]) - 1;
          const d = Number(parts[2]);
          if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
            return new Date(y, m, d);
          }
        }
        const dt = new Date(value);
        return isNaN(dt.getTime()) ? null : dt;
      }
      return null;
    }

}

function pad(n: number) { return String(n).padStart(2, '0'); }

// helper to format Date|string to yyyy-MM-dd
function toBackendDateLocal(v: any): string | null {
  if (!v) return null;
  if (v instanceof Date) {
    return `${v.getFullYear()}-${pad(v.getMonth()+1)}-${pad(v.getDate())}`;
  }
  // try parse string as ISO or yyyy-MM-dd
  const dt = new Date(v);
  if (!isNaN(dt.getTime())) {
    return `${dt.getFullYear()}-${pad(dt.getMonth()+1)}-${pad(dt.getDate())}`;
  }
  return null;
}
