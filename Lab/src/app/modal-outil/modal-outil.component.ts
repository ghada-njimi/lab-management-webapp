import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToolService } from 'src/Services/tool.service';
import { Outil } from 'src/Models/Outil';

@Component({
  selector: 'app-modal-outil',
  templateUrl: './modal-outil.component.html',
  styleUrls: ['./modal-outil.component.css']
})
export class ModalOutilComponent implements OnInit {
  form!: FormGroup;
  isEditMode = false;
  outilId: number | null = null;

  constructor(
    public dialogRef: MatDialogRef<ModalOutilComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private toolService: ToolService
  ) {
    this.form = new FormGroup({
      source: new FormControl(null),
      date: new FormControl(null)
    });
  }

  ngOnInit(): void {
    if (this.data) {
      this.isEditMode = true;
      this.outilId = this.data;
      this.toolService.getOutilById(this.data).subscribe({
        next: (outil: Outil) => {
          this.form.patchValue({
            source: outil.source,
            date: this.parseDate(outil.date)
          });
        }
      });
    }
  }

  save() {
    const formValue = this.form.value;
    // ensure date is sent as yyyy-MM-dd
    if (formValue.date) {
      formValue.date = this.formatDateToBackend(formValue.date);
    }
    if (this.isEditMode && this.outilId) {
      formValue.id = this.outilId;
    }
    this.dialogRef.close(formValue);
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

  private formatDateToBackend(date: any): string | null {
    if (!date) return null;
    if (typeof date === 'string') return date;
    if (date instanceof Date) {
      const y = date.getFullYear();
      const m = String(date.getMonth() + 1).padStart(2, '0');
      const d = String(date.getDate()).padStart(2, '0');
      return `${y}-${m}-${d}`;
    }
    return String(date);
  }
}
