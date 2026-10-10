package joao.alves.delivery.marketplace.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pedido pedido;

    @Column(name = "forma_pagamento", nullable = false)
    private Forma formaPagamento;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "status_pagamento", nullable = false)
    private StatusPagamento statusPagamento = StatusPagamento.AGUARDANDO;

    public enum Forma {
        PIX,
        CARTAO,
        DINHEIRO
    }

    public enum StatusPagamento {
        AGUARDANDO,
        CONCLUIDO,
        CANCELADO,
        EXPIRADO
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Forma getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(Forma formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
}
