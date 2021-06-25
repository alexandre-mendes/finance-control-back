package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.WalletDTO;
import br.com.financeirojavaspring.model.Account;
import br.com.financeirojavaspring.model.User;
import br.com.financeirojavaspring.model.Wallet;
import br.com.financeirojavaspring.repository.WalletRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

  private WalletRepository walletRepository;
  private ModelMapper modelMapper;
  private UserAuthenticationService authenticationService;

  @Autowired
  public WalletService(WalletRepository walletRepository,
      ModelMapper modelMapper,
      UserAuthenticationService authenticationService) {
    this.walletRepository = walletRepository;
    this.modelMapper = modelMapper;
    this.authenticationService = authenticationService;
  }

  public WalletDTO save(WalletDTO walletDTO) {
    var account = authenticationService.getUser().getAccount();
    var wallet = modelMapper.map(walletDTO, Wallet.class);
    wallet.setUuid(UUID.randomUUID().toString());
    wallet.setAccount(account);
    wallet = walletRepository.save(wallet);
    return modelMapper.map(wallet, WalletDTO.class);
  }

  public WalletDTO update(WalletDTO walletDTO) {
    var walletSaved = walletRepository.findByUuid(walletDTO.getUuid());
    walletSaved.setTitle(walletDTO.getTitle());
    walletSaved.setTypeWallet(walletDTO.getTypeWallet());
    walletSaved = walletRepository.save(walletSaved);
    return modelMapper.map(walletSaved, WalletDTO.class);
  }

  public List<WalletDTO> findAll() {
    var account = authenticationService.getUser().getAccount();
    var wallets = walletRepository.findAllByAccount(account);
    return wallets.stream().map(w -> modelMapper.map(w, WalletDTO.class)).collect(Collectors.toList());
  }
}
