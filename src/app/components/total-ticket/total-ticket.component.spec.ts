import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TotalTicketComponent } from './total-ticket.component';

describe('TotalTicketComponent', () => {
  let component: TotalTicketComponent;
  let fixture: ComponentFixture<TotalTicketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TotalTicketComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TotalTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
