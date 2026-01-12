import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Outil } from 'src/Models/Outil';
import { ToolService } from 'src/Services/tool.service';
import { ModalOutilComponent } from '../modal-outil/modal-outil.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-tool',
  templateUrl: './tool.component.html',
  styleUrls: ['./tool.component.css']
})
export class ToolComponent implements AfterViewInit {
  dataSource!: MatTableDataSource<Outil>;
  displayedColumns: string[] = ['id', 'source', 'date', 'actions'];
  isLoading = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private toolService: ToolService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.dataSource = new MatTableDataSource<Outil>([]);
    this.loadTools();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadTools() {
    this.isLoading = true;
    this.toolService.getAllOutils().subscribe({
      next: (data) => {
        this.dataSource.data = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error("Erreur lors du chargement des outils:", err);
        this.showMessage("Erreur lors du chargement des outils");
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
    const dialogRef = this.dialog.open(ModalOutilComponent, {
      width: '600px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe((outilRecupere: Outil) => {
      if (outilRecupere) {
        this.toolService.addOutil(outilRecupere).subscribe({
          next: () => {
            this.showMessage("Outil créé avec succès");
            this.loadTools();
          },
          error: (err) => {
            console.error("Erreur lors de la sauvegarde de l'outil:", err);
            this.showMessage("Erreur lors de la création de l'outil");
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

    const dialogRef = this.dialog.open(ModalOutilComponent, dialogConfig);

    dialogRef.afterClosed().subscribe((resForm: Outil) => {
      if (resForm) {
        this.toolService.updateOutil(id, resForm).subscribe({
          next: () => {
            this.showMessage("Outil modifié avec succès");
            this.loadTools();
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
    if (confirm("Êtes-vous sûr de vouloir supprimer cet outil ?")) {
      this.toolService.deleteOutil(id).subscribe({
        next: () => {
          this.showMessage("Outil supprimé avec succès");
          this.loadTools();
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