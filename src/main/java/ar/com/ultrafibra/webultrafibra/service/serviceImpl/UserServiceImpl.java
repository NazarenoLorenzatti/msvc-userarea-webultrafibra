package ar.com.ultrafibra.webultrafibra.service.serviceImpl;

import ar.com.ultrafibra.webultrafibra.daoRepositories.iUserDao;
import ar.com.ultrafibra.webultrafibra.models.Client;
import ar.com.ultrafibra.webultrafibra.models.User;
import ar.com.ultrafibra.webultrafibra.response.UserResponseRest;
import ar.com.ultrafibra.webultrafibra.service.iUserService;
import ar.com.ultrafibra.webultrafibra.service.util.EmailService;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Data
public class UserServiceImpl implements iUserService {

    @Autowired
    private iUserDao userDao;

    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private EmailService emailService;

    @Override
    public ResponseEntity<UserResponseRest> saveUser(User user) {
        UserResponseRest respuesta = new UserResponseRest();
        List<User> users = new ArrayList();
        if (user != null) {
//            Optional<User> o = userDao.findByEmail(user.getEmail());
            Optional<User> userByDni = userDao.findByIdentityNumber(user.getIdentityNumber());
            if (!userByDni.isPresent()) {
                Client client = this.clientService.findByDocumentNumber(user.getIdentityNumber());
                if (client != null) {
                    user.setToken(generateConfirmationToken());
                    User newUser = userDao.save(user);
                    if (newUser != null) {
                        users.add(newUser);
                        try {
                            emailService.enviarMail(user.getEmail(), "Confirmacion de Mail", "http://119.8.72.246/#/email-confirm/" + newUser.getToken());
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        respuesta.getUserResponse().setUsers(users);
                        respuesta.setMetadata("Ok", "00", "Nuevo Usuario Guardado");
                        return new ResponseEntity<>(respuesta, HttpStatus.OK);
                    } else {
                        respuesta.setMetadata("nOk", "01", "No se pudo Guardar el nuevo usuario, error en el servidor");
                        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    respuesta.setMetadata("nOk", "01", "No se pudo Guardar el nuevo usuario, no existe como cliente");
                    return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
                }
            } else {
                users.add(userByDni.get());
                respuesta.getUserResponse().setUsers(users);
                respuesta.setMetadata("nOk", "01", "El DNI ya se encuentran registrados con el mail " + userByDni.get().getEmail() + " Si cometio un error al registrarse aguarde 24 hs y podra volver a hacerlo - Si este no es su mail comuniquese con nosotros");
                return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
            }

        } else {
            respuesta.setMetadata("nOk", "01", "Error en la Consulta");
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<UserResponseRest> editPassword(User user) {
        UserResponseRest respuesta = new UserResponseRest();
        List<User> users = new ArrayList();
        if (user != null) {
            Optional<User> o = userDao.findById(user.getId());
            if (o.isPresent()) {
                User newUser = new User();
                newUser.setPassword(user.getPassword());
                newUser = userDao.save(newUser);
                if (newUser != null) {
                    users.add(newUser);
                    respuesta.getUserResponse().setUsers(users);
                    respuesta.setMetadata("Ok", "00", "Nuevo Password Guardado");
                    return new ResponseEntity<>(respuesta, HttpStatus.OK);
                } else {
                    respuesta.setMetadata("nOk", "01", "No se pudo Guardar el nuevo Password, error en el servidor");
                    return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
                }
            } else {
                respuesta.setMetadata("nOk", "01", "No se pudo Guardar el nuevo Password, no existe el usuario");
                return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
            }

        } else {
            respuesta.setMetadata("nOk", "01", "Error en la Consulta");
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<UserResponseRest> editEmail(User user) {
        UserResponseRest respuesta = new UserResponseRest();
        List<User> users = new ArrayList();
        if (user != null) {
            Optional<User> o = userDao.findById(user.getId());
            if (o.isPresent()) {
                User newUser = new User();
                newUser.setEmail(user.getEmail());
                newUser.setToken(generateConfirmationToken());
                newUser.setVerified(false);
                newUser = userDao.save(newUser);
                if (newUser != null) {
                    users.add(newUser);
                    try {
                        emailService.enviarMail(user.getEmail(), "Confirmacion de Mail", "https://ultrafibra.com.ar/#/email-confirm/" + newUser.getToken());
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    respuesta.getUserResponse().setUsers(users);
                    respuesta.setMetadata("Ok", "00", "Nuevo Usuario Guardado");
                    return new ResponseEntity<>(respuesta, HttpStatus.OK);
                } else {
                    respuesta.setMetadata("nOk", "01", "No se pudo Guardar el nuevo usuario, error en el servidor");
                    return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
                }
            } else {
                respuesta.setMetadata("nOk", "01", "No se pudo Guardar el nuevo usuario, no existe como cliente");
                return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
            }

        } else {
            respuesta.setMetadata("nOk", "01", "Error en la Consulta");
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<UserResponseRest> confirmEmail(String token) {
        UserResponseRest respuesta = new UserResponseRest();
        List<User> users = new ArrayList();
        if (token != null) {
            Optional<User> o = userDao.findByToken(token);
            if (o.isPresent()) {
                User user = o.get();
                user.setVerified(true);
                user = userDao.save(user);
                if (user != null) {
                    users.add(user);
                    respuesta.getUserResponse().setUsers(users);
                    respuesta.setMetadata("Ok", "00", "Email del usuario Confirmado");
                    return new ResponseEntity<>(respuesta, HttpStatus.OK);
                } else {
                    respuesta.setMetadata("nOk", "01", "No se pudo confirmar  el mail por que ocurrio un error Grave");
                    return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {
                respuesta.setMetadata("nOk", "01", "No se encontro el Usuario");
                return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
            }
        } else {
            respuesta.setMetadata("nOk", "01", "Error en la Consulta");
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<UserResponseRest> forwardEmail(String email) {
        UserResponseRest respuesta = new UserResponseRest();
        List<User> users = new ArrayList();
        if (email != null) {
            Optional<User> o = userDao.findByEmail(email);
            if (o.isPresent()) {
                try {
                    emailService.enviarMail(email, "Confirmacion de Mail", "https://ultrafibra.com.ar/#/email-confirm/" + o.get().getToken());
                    users.add(o.get());
                    respuesta.getUserResponse().setUsers(users);
                    respuesta.setMetadata("Ok", "00", "Se Reenvio el Mail");
                    return new ResponseEntity<>(respuesta, HttpStatus.OK);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    respuesta.setMetadata("nOk", "01", "Ocurrio un Error al internetar reenviar el mail");
                    return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                respuesta.setMetadata("nOk", "01", "No se encontro el Usuario con ese mail");
                return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
            }
        } else {
            respuesta.setMetadata("nOk", "01", "Error en la Consulta");
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<UserResponseRest> forwardEmailByDni(String dni) {
        UserResponseRest respuesta = new UserResponseRest();
        if (dni != null) {
            Optional<User> o = userDao.findByIdentityNumber(dni);
            if (o.isPresent()) {
                try {
                    emailService.enviarMail(o.get().getEmail(), "Confirmacion de Mail", "https://ultrafibra.com.ar/#/email-confirm/" + o.get().getToken());
                    respuesta.setMetadata("Ok", "00", "Reenviamos el mail a la Casilla " + o.get().getEmail() + " Recorda revisar la carpeta de Spam");
                    return new ResponseEntity<>(respuesta, HttpStatus.OK);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    respuesta.setMetadata("nOk", "01", "Ocurrio un Error al internetar reenviar el mail");
                    return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                respuesta.setMetadata("nOk", "01", "No se encontro el Usuario con ese mail");
                return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
            }
        } else {
            respuesta.setMetadata("nOk", "01", "Error en la Consulta");
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<UserResponseRest> recoveryPassword(String email, String dni) {
        UserResponseRest respuesta = new UserResponseRest();
        if (email != null) {
            Optional<User> o = userDao.findByIdentityNumber(dni);
            if (o.isPresent()) {
                if (o.get().getEmail().equals(email)) {
                    try {
                        emailService.recoveryPasswordEmail(email, "Recuperar Contraseña", o.get().getPassword());
                        respuesta.setMetadata("Ok", "00", "Se Reenvio la contraseña al mail propuesto");
                        return new ResponseEntity<>(respuesta, HttpStatus.OK);
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                        respuesta.setMetadata("nOk", "01", "Ocurrio un Error al internetar reenviar el mail");
                        return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    respuesta.setMetadata("nOk", "01", "El Dni no esta asociado con este Email, si no lo recuerda contactese con nosotros");
                    return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
                }
            } else {
                respuesta.setMetadata("nOk", "01", "No se encontro un Usuario con el Dni Otorgado");
                return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
            }
        } else {
            respuesta.setMetadata("nOk", "01", "Error en la Consulta");
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public User findUser(User user) {
        if (user != null) {
            Optional<User> o = userDao.findByIdentityNumber(user.getIdentityNumber());
            if (o.isPresent()) {
                return o.get();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private String generateConfirmationToken() {
        UUID uuid = UUID.randomUUID();
        return Base64.encodeBase64URLSafeString(uuid.toString().getBytes());
    }

}
