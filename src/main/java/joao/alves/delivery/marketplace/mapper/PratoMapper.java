package joao.alves.delivery.marketplace.mapper;

import joao.alves.delivery.marketplace.domain.Categoria;
import joao.alves.delivery.marketplace.domain.Prato;
import joao.alves.delivery.marketplace.dto.request.PratoRequest;
import joao.alves.delivery.marketplace.dto.response.PratoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PratoMapper {

    public Prato toEntity(PratoRequest pratoRequest) {
        Prato prato = new Prato();

        Categoria categoria = new Categoria();
        categoria.setId(pratoRequest.categoriaId());

        prato.setCategoria(categoria);
        prato.setNome(pratoRequest.nome());
        prato.setPreco(pratoRequest.preco());
        prato.setDisponivel(pratoRequest.disponivel());
        return prato;
    }

    public PratoResponse toDTO(Prato prato) {
        return new PratoResponse(
                prato.getId(), prato.getCategoria().getId(), prato.getNome(),
                prato.getPreco() ,prato.getDisponivel()
        );
    }

}
