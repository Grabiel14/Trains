package mmt.app.main;

import java.io.IOException;

import mmt.TicketOffice;
import mmt.exceptions.MissingFileAssociationException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;


/**
 * §3.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<TicketOffice> {

  private Input<String> _filename;


  /**
   * @param receiver
   */
  public DoSave(TicketOffice receiver) {
    super(Label.SAVE, receiver);
		_filename = _form.addStringInput(Message.newSaveAs());
  }

  /** @see pt.tecnico.po.ui.Command#execute()
    * @see mmt.TicketOffice#save()
    * @see mmt.TicketOffice#save(String)
    */
  @Override
  public final void execute() {
		try {
      _receiver.save();
    } catch (MissingFileAssociationException e) {
			_form.parse();
			try {
        _receiver.save(_filename.value());
      } catch (IOException io) {
  			io.printStackTrace();
  		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
