package mmt.app.itineraries;

import mmt.TicketOffice;
import mmt.app.exceptions.BadDateException;
import mmt.app.exceptions.BadTimeException;
import mmt.app.exceptions.NoSuchItineraryException;
import mmt.app.exceptions.NoSuchPassengerException;
import mmt.app.exceptions.NoSuchStationException;
import mmt.exceptions.BadDateSpecificationException;
import mmt.exceptions.BadTimeSpecificationException;
import mmt.exceptions.NoSuchPassengerIdException;
import mmt.exceptions.NoSuchStationNameException;
import mmt.exceptions.NoSuchItineraryChoiceException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import java.util.List;

/**
 * §3.4.3. Add new itinerary.
 */
public class DoRegisterItinerary extends Command<TicketOffice> {

  private Input<Integer> _passengerId;
  private Input<String> _departureStation;
  private Input<String> _departureTime;
  private Input<String> _arrivalStation;
  private Input<String> _departureDate;
  private Input<Integer> _itineraryNumber;

  /**
   * @param receiver
   */
  public DoRegisterItinerary(TicketOffice receiver) {
    super(Label.REGISTER_ITINERARY, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute()
    * @see mmt.TicketOffice#search(int,String,String,String,String)
    * @see mmt.TicketOffice#commitItinerary(int,int)
    */
  @Override
  public final void execute() throws DialogException {

    _form.clear();
    _passengerId = _form.addIntegerInput(Message.requestPassengerId());
    _departureStation = _form.addStringInput(Message.requestDepartureStationName());
    _arrivalStation = _form.addStringInput(Message.requestArrivalStationName());
    _departureDate = _form.addStringInput(Message.requestDepartureDate());
    _departureTime = _form.addStringInput(Message.requestDepartureTime());
    _form.parse();

    try {
      List<String> itineraries = _receiver.search(_passengerId.value(),
_departureStation.value(), _arrivalStation.value(), _departureDate.value(), _departureTime.value());
      if(itineraries.size() > 0) {
        for(String s : itineraries)
          _display.addLine(s);
        _display.display();
        _form.clear();
        _itineraryNumber = _form.addIntegerInput(Message.requestItineraryChoice());
        _form.parse();
        _receiver.commitItinerary(_passengerId.value(),_itineraryNumber.value());
      }
    } catch (NoSuchPassengerIdException e) {
      throw new NoSuchPassengerException(e.getId());
    } catch (NoSuchStationNameException e) {
      throw new NoSuchStationException(e.getName());
    } catch (NoSuchItineraryChoiceException e) {
      throw new NoSuchItineraryException(e.getPassengerId(), e.getItineraryId());
    } catch (BadDateSpecificationException e) {
      throw new BadDateException(e.getDate());
    } catch (BadTimeSpecificationException e) {
      throw new BadTimeException(e.getTime());
    }
  }
}
