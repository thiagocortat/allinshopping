package com.projetandoo.allinshopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.androidquery.AQuery;
import com.projetandoo.allinshopping.alerts.ErrorAlert;
import com.projetandoo.allinshopping.components.MaskedWatcher;
import com.projetandoo.allinshopping.events.FormatarDataNascimentoLostFocus;
import com.projetandoo.allinshopping.exceptions.NoUniqueRegistryException;
import com.projetandoo.allinshopping.models.Cliente;
import com.projetandoo.allinshopping.models.Endereco;
import com.projetandoo.allinshopping.models.Estado;
import com.projetandoo.allinshopping.models.Pedido;
import com.projetandoo.allinshopping.repository.EstadoRepository;
import com.projetandoo.allinshopping.services.ClienteService;
import com.projetandoo.allinshopping.utilities.Constante;
import com.projetandoo.allinshopping.utilities.ParseUtilities;
import com.projetandoo.allinshopping.utilities.PriceUtilities;
import org.apache.commons.validator.GenericValidator;

import java.util.Date;
import java.util.List;


public class ClienteActivity extends AbstractActivity
        implements
        OnClickListener {
    private AQuery aq;
    private Long id = 0L;

    private Cliente mCliente;

    private void loadFromCliente(Cliente cliente) {

        this.setId(cliente.getId());
        this.setPrimeiroNome(cliente.getPrimeiroNome());
        this.setUltimoNome(cliente.getUltimoNome());
        this.setDataNascimento(cliente.getDataNascimento());
        this.setEmail(cliente.getEmail());
        this.setCidade(cliente.getEndereco().getCidade());
        this.setBairro(cliente.getEndereco().getBairro());
        this.setEndereco(cliente.getEndereco().getLogradouro());
        this.setCep(cliente.getEndereco().getCep());
        setEstado(cliente.getEndereco().getEstado());
        this.setTelefone(cliente.getEndereco().getTelefone());
        this.setCelular(cliente.getEndereco().getCelular());

    }

    public void setCelular(String celular) {

        try {
            celular = celular.replace("(", "").replace(")", "").replace("-", "");
        }catch (Exception e){
            e.printStackTrace();
        }
        aq.id(R.id.celular).text(celular);
    }

    public void setTelefone(String telefone) {
        try {
            telefone = telefone.replace("(", "").replace(")", "").replace("-", "");
        }catch (Exception e){
            e.printStackTrace();
        }

        aq.id(R.id.telefone).text(telefone);
    }

    private void setId(Long id) {
        this.id = id;
    }

    public void setCep(String cep) {

        try {
            cep = cep.replace("-", "");
        }catch (Exception e){
            e.printStackTrace();
        }

        aq.id(R.id.cep).text(cep);
    }


    public void setEndereco(String endereco) {
        aq.id(R.id.endereco).text(endereco);
    }

    public void setBairro(String bairro) {
        aq.id(R.id.bairro).text(bairro);
    }

    public void setCidade(String cidade) {
        aq.id(R.id.cidade).text(cidade);
    }

    public void setEmail(String email) {
        aq.id(R.id.email).text(email);
    }

    public void setDataNascimento(Date dataNascimento) {
        aq.id(R.id.dataNascimento).text(ParseUtilities.formatDate(dataNascimento, Constante.DATE_FOR_MASK_FORMAT));
    }

    public void setNome(String nome) {
        aq.id(R.id.nomeCompleto).text(nome);
    }

    @SuppressWarnings("unchecked")
    public void setEstado(Estado estado) {
        Spinner estados = aq.id(R.id.estados).getSpinner();
        ArrayAdapter<Estado> adapter = (ArrayAdapter<Estado>) estados.getAdapter();
        int position = adapter.getPosition(estado);
        estados.setSelection(position);
    }

    public Estado getEstado() {
        return (Estado) aq.id(R.id.estados).getSelectedItem();
    }

    private Cliente createCliente() {

        if (mCliente == null)
            mCliente = new Cliente();
        if (mCliente.getId() == null || mCliente.getId() == 0)
            mCliente.setId(this.id);
        if (mCliente.getEndereco() == null)
            mCliente.setEndereco(new Endereco());

        mCliente.setDataNascimento(getDataNascimento());
        mCliente.setEmail(this.getEmail());
        mCliente.setPrimeiroNome(this.getPrimeiroNome());
        mCliente.setUltimoNome(this.getUltimoNome());

        mCliente.getEndereco().setEstado(this.getEstado());
        mCliente.getEndereco().setTelefone(getTelefone());
        mCliente.getEndereco().setCelular(getCelular());
        mCliente.getEndereco().setCep(this.getCep());
        mCliente.getEndereco().setBairro(this.getBairro());
        mCliente.getEndereco().setCidade(this.getCidade());
        mCliente.getEndereco().setLogradouro(this.getEndereco());
        return mCliente;

    }

    public String getCelular() {
        return aq.id(R.id.celular).getText().toString();
    }

    public String getTelefone() {
        return aq.id(R.id.telefone).getText().toString();
    }

    public String getCep() {
        return aq.id(R.id.cep).getText().toString();
    }

    public String getEndereco() {
        return aq.id(R.id.endereco).getText().toString();
    }

    public String getBairro() {
        return aq.id(R.id.bairro).getText().toString();
    }

    public String getCidade() {
        return aq.id(R.id.cidade).getText().toString();
    }

    public String getEmail() {
        return aq.id(R.id.email).getText().toString();
    }

    public Date getDataNascimento() {
        String dt = aq.id(R.id.dataNascimento).getText().toString();
        if(!GenericValidator.isBlankOrNull(dt)) {
            return ParseUtilities.toDate(dt, Constante.DATE_LONG_FORMAT);
        }else{
            return null;
        }
    }

    public void setPrimeiroNome(String primeiroNome) {
        aq.id(R.id.primeiroNome).text(primeiroNome);
    }

    public void setUltimoNome(String ultimoNome) {
        aq.id(R.id.ultimoNome).text(ultimoNome);
    }


    public String getPrimeiroNome() {
        return aq.id(R.id.primeiroNome).getText().toString();
    }

    public String getUltimoNome() {
        return aq.id(R.id.ultimoNome).getText().toString();
    }

    private void salvar() {

        try {
            Pedido pedido = PriceUtilities.getPedido();

            ClienteService clienteservice = new ClienteService(this);
            Date dataNascimento = this.getDataNascimento();

            if ( dataNascimento == null ) {
                (new ErrorAlert(this))
                        .setTitle("Não foi possível salvar o cliente.")
                        .setCancelable(true)
                        .setMessage(
                                "Não foi possível salvar o cliente. A data de nascimento inválida")
                        .show();
            } else {
                final Cliente cliente = createCliente();
                if (!cliente.isValid()) {
                    (new ErrorAlert(this))
                            .setTitle("Não foi possível salvar o cliente.")
                            .setCancelable(true)
                            .setMessages(cliente.errors())
                            .show();
                } else {
                    clienteservice.save(cliente);
                    pedido.setCliente(cliente);
                    Intent intent = new Intent(this, PagamentoActivity.class);
                    startActivity(intent);
                    this.finish();
                }

            }
        } catch (NoUniqueRegistryException e) {
            (new ErrorAlert(this))
                    .setTitle("Não foi possível salvar o cliente.")
                    .setCancelable(true)
                    .setMessage(e.getMessage())
                    .show();
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.salvar) {
            salvar();
        } else {
//            Intent intent = new Intent(this, HomeActivity.class);
//            startActivity(intent);
            this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_cliente_main);
        aq = new AQuery(this);
        Spinner spinner = aq.id(R.id.estados).getSpinner();
        List<Estado> estados = new EstadoRepository(this).getAll();
        estados.add(0, new Estado("", "Selecione um Estado"));
        ArrayAdapter<Estado> arrayadapter = new ArrayAdapter<Estado>(this, android.R.layout.simple_list_item_1, estados);
        spinner.setAdapter(arrayadapter);

        aq.id(R.id.salvar).clicked(this);
        aq.id(R.id.cancelar).clicked(this);
        aq.id(R.id.dataNascimento).getEditText().setOnFocusChangeListener(new FormatarDataNascimentoLostFocus());

        new MaskedWatcher("##/##/####", aq.id(R.id.dataNascimento).getEditText()).setAcceptOnlyNumbers(true);
        new MaskedWatcher("#####-###", aq.id(R.id.cep).getEditText()).setAcceptOnlyNumbers(true);
        new MaskedWatcher("(##)#####-####", aq.id(R.id.celular).getEditText()).setAcceptOnlyNumbers(true);
        new MaskedWatcher("(##)####-####", aq.id(R.id.telefone).getEditText()).setAcceptOnlyNumbers(true);

       mCliente = (Cliente) getIntent().getExtras().get(Constante.CLIENTE);

        if (mCliente != null) {
            loadFromCliente(mCliente);
        }
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this, ShoppingCartActivity.class);
//        startActivity(intent);
//        this.finish();
//    }
}
