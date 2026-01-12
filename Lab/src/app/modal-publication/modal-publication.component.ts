import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PublicationService } from 'src/Services/publication.service';
import { Publication } from 'src/Models/Publication';

@Component({
  selector: 'app-modal-publication',
  templateUrl: './modal-publication.component.html',
  styleUrls: ['./modal-publication.component.css']
})
export class ModalPublicationComponent implements OnInit {
  form!: FormGroup;
  isEditMode = false;
  publicationId: number | null = null;
  publicationTypes = [
    'Article de journal',
    'Conférence',
    'Chapitre de livre',
    'Livre',
    'Thèse',
    'Rapport technique',
    'Prépublication'
  ];

  constructor(
    public dialogRef: MatDialogRef<ModalPublicationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private publicationService: PublicationService
  ) {
    this.form = new FormGroup({
      titre: new FormControl(null),
      type: new FormControl(null),
      dateApparition: new FormControl(null),
      lien: new FormControl(null),
      source: new FormControl(null)
    });
  }

  ngOnInit(): void {
    if (this.data) {
      this.isEditMode = true;
      this.publicationId = this.data;
      this.publicationService.getPublicationById(this.data).subscribe({
        next: (publication: Publication) => {
          this.form.patchValue({
            titre: publication.titre,
            type: publication.type,
            dateApparition: publication.dateApparition,
            lien: publication.lien,
            source: publication.source
          });
        }
      });
    }
  }

  save() {
    const formValue = this.form.value;
    if (this.isEditMode && this.publicationId) {
      formValue.id = this.publicationId;
    }
    this.dialogRef.close(formValue);
  }

  close() {
    this.dialogRef.close();
  }
}
