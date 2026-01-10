import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Evt } from 'src/Models/Evt';
import { EvtService } from 'src/Services/evt.service';
import { ModalEvtComponent } from '../modal-evt/modal-evt.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-evt',
  templateUrl: './evt.component.html',
  styleUrls: ['./evt.component.css']
})
export class EvtComponent implements AfterViewInit {
  dataSource!: MatTableDataSource<Evt>;
  displayedColumns: string[] = ['id', 'titre', 'date', 'lieu', 'actions'];
  isLoading = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private ES: EvtService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.dataSource = new MatTableDataSource<Evt>([]);
    this.loadEvents();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadEvents() {
    this.isLoading = true;
    this.ES.GetAllEvts().subscribe({
      next: (data) => {
        this.dataSource.data = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error("Erreur lors du chargement des événements:", err);
        this.showMessage("Erreur lors du chargement des événements");
        this.isLoading = false;
      }
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  open() {
    const dialogRef = this.dialog.open(ModalEvtComponent, {
      width: '600px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe((evtRecupere: Evt) => {
      if (evtRecupere) {
        this.ES.saveEvent(evtRecupere).subscribe({
          next: () => {
            this.showMessage("Événement créé avec succès");
            this.loadEvents();
          },
          error: (err) => {
            console.error("Erreur lors de la sauvegarde de l'événement:", err);
            this.showMessage("Erreur lors de la création de l'événement");
          }
        });
      }
    });
  }

  openEdit(id: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = id;
    dialogConfig.width = '600px';
    dialogConfig.disableClose = true;

    const dialogRef = this.dialog.open(ModalEvtComponent, dialogConfig);

    dialogRef.afterClosed().subscribe((resForm: Evt) => {
      if (resForm) {
        this.ES.updateEvenement(id.toString(), resForm).subscribe({
          next: () => {
            this.showMessage("Événement modifié avec succès");
            this.loadEvents();
          },
          error: (err) => {
            console.error("Erreur lors de la modification:", err);
            this.showMessage("Erreur lors de la modification");
          }
        });
      }
    });
  }

  delete(id: number) {
    if (confirm("Êtes-vous sûr de vouloir supprimer cet événement ?")) {
      this.ES.deleteEvenement(id.toString()).subscribe({
        next: () => {
          this.showMessage("Événement supprimé avec succès");
          this.loadEvents();
        },
        error: (err) => {
          console.error("Erreur lors de la suppression:", err);
          this.showMessage("Erreur lors de la suppression");
        }
      });
    }
  }

  private showMessage(message: string) {
    this.snackBar.open(message, 'Fermer', {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'top'
    });
  }
}