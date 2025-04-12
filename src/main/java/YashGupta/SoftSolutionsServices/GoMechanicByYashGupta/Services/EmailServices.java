package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services;


import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ScheduleService;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EmailServices {

    @Autowired
    private JavaMailSender mailSender;


    public void sendUserSignupEmail(String toEmail, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to GoMechanic!");

        message.setText("Dear " + name + ",\n\n" +
                "You have successfully signed up on GoMechanic!\n\n" +
                "Welcome to GoMechanic – your reliable partner for all vehicle service needs.\n\n" +
                " Whether your vehicle breaks down *on the road* or you want to *schedule a service in advance*, we've got you covered:\n" +
                "• **On-Way Assistance** – Request immediate help from a nearby mechanic during breakdowns.\n" +
                "• **Scheduled Service** – Plan and book services at your convenience, right from your home.\n\n" +
                "We're committed to keeping your journeys smooth and stress-free.\n" +
                "You can now log in anytime to explore our services and request assistance easily.\n\n" +
                " **Need Help?** If you have any questions or require support, feel free to reach out to us at:\n" +
                "guptay264@gmail.com\n\n" +
                "Warm Regards,\n" +
                "GoMechanic by Yash\n" +
                "Technical Support Team");

        mailSender.send(message);
    }




    public void sendAdminCreationEmail(String toEmail, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Admin Account Created - GoMechanic by Yash");

        message.setText("Dear " + name + ",\n\n" +
                "Congratulations! Your account has been successfully created with ADMIN privileges.\n" +
                "You now have full administrative access to the GoMechanic by Yash platform.\n\n" +
                "As an admin, you can manage users, service providers, bookings, and system configurations.\n" +
                "Please ensure to keep your credentials safe and confidential.\n\n" +
                "Best Regards,\n" +
                "GoMechanic by Yash\n" +
                "Technical Support Team");

        mailSender.send(message);
    }


    public void sendServiceProviderCreationEmail(String toEmail, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Mechanic Account Created - GoMechanic by Yash");

        message.setText("Dear " + name + ",\n\n" +
                "Welcome aboard! Your mechanic/service provider account has been successfully created on the GoMechanic by Yash platform.\n\n" +
                "You can now start receiving bookings, manage your availability, and assist users with their vehicle issues in real-time.\n" +
                "Please make sure to keep your login credentials secure and update your profile for better visibility.\n\n" +
                "We’re excited to have you as part of the GoMechanic service team.\n\n" +
                "Best Regards,\n" +
                "GoMechanic by Yash\n" +
                "Technical Support Team");

        mailSender.send(message);
    }



    public void sendServiceRequestEmailToServiceProvider(ServiceRequest request, String serviceProviderEmail, String providerName) {

        String subject = "🚨 Immediate Service Request from GoMechanic";

        String body = "Dear " + providerName + ",\n\n" +
                "You have received a new *immediate* service request from a customer.\n\n" +

                "🧾 Request Details:\n" +
                "• Request Number: " + request.getRequestnumber() + "\n" +
                "• Customer Name: " + request.getCustomername() + "\n" +
                "• Customer ID: " + request.getUserid() + "\n" +
                "• Vehicle Type: " + request.getTypeofvehicle() + "\n" +
                "• Vehicle Number: " + request.getVehiclenumber() + "\n" +
                "• Issue Reported: " + request.getServicetype() + "\n" +
                "• Contact Number: " + request.getUsercontact() + "\n\n" +

                "📍 Location:\n" +
                "• Current Address: " + request.getCurraddress() + "\n" +
                "• Area: " + request.getArea() + "\n" +
                "• Nearby Landmark: " + request.getNearby() + "\n\n" +

                "⏱ *Immediate Action Required:*\n" +
                "Please contact the customer within the next 5 minutes to offer assistance and begin service.\n\n" +

                "Thank you for being a part of GoMechanic by Yash!\n\n" +
                "Warm regards,\n" +
                "GoMechanic Support Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(serviceProviderEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }




    public void sendScheduledServiceEmailToServiceProvider(ScheduleService request, String serviceProviderEmail, String providerName) {

        String subject = "📅 New Scheduled Service Request from GoMechanic";

        String body = "Dear " + providerName + ",\n\n" +
                "You have received a new *scheduled* service request from a customer.\n\n" +
                "🧾 Request Details:\n" +
                "• Request Number: " + request.getRequestnumber() + "\n" +
                "• Customer Name: " + request.getCustomername() + "\n" +
                "• Customer ID: " + request.getUserid() + "\n" +
                "• Vehicle Type: " + request.getTypeofvehicle() + "\n" +
                "• Vehicle Number: " + request.getVehiclenumber() + "\n" +
                "• Issue Reported: " + request.getServicetype() + "\n" +
                "• Contact Number: " + request.getUsercontact() + "\n\n" +

                "📍 Location:\n" +
                "• Complete Address: " + request.getCompleteaddress() + "\n" +
                "• Area: " + request.getArea() + "\n" +
                "• Nearby: " + request.getNearby() + "\n\n" +

                "📆 Scheduled Service Details:\n" +
                "• Date: " + request.getScheduleddate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                "• Time: " + request.getScheduledtime().format(DateTimeFormatter.ofPattern("hh:mm a")) + "\n\n" +


                "🛠 Please ensure availability at the scheduled time. Contact the customer if needed.\n\n" +
                "Thank you for your continued support with GoMechanic by Yash!\n\n" +
                "Warm regards,\n" +
                "GoMechanic Support Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(serviceProviderEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }






    public void sendUserOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("User Registration - OTP from GoMechanic by Yash");

        message.setText(
                "Hello,\n\n" +
                        "Thank you for choosing GoMechanic by Yash!\n\n" +
                        "Your One-Time Password (OTP) for email verification is:\n\n" +
                        "    " + otp + "\n\n" +
                        "🕒 This OTP is valid for 5 minutes only.\n\n" +
                        "🔒 Please do not share this OTP with anyone. GoMechanic will never ask for it.\n\n" +
                        "If you did not request this OTP, please ignore this email.\n\n" +
                        "Best regards,\n" +
                        "GoMechanic by Yash\n" +
                        "Customer Support Team"
        );

        mailSender.send(message);
    }




    public void sendServiceProviderOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Service Provider Email Verification - OTP from GoMechanic by Yash");

        message.setText(
                "Hello Service Provider,\n\n" +
                        "We received a request to register you as a Service Provider on GoMechanic by Yash.\n\n" +
                        "Your One-Time Password (OTP) for email verification is:\n\n" +
                        "    " + otp + "\n\n" +
                        "🕒 This OTP is valid for 5 minutes only.\n\n" +
                        "🔒 Please do not share this OTP with anyone. GoMechanic will never ask for it.\n\n" +
                        "If you did not initiate this request, you can safely ignore this email.\n\n" +
                        "Thank you for joining us!\n\n" +
                        "Best regards,\n" +
                        "GoMechanic by Yash\n" +
                        "Service Provider Onboarding Team"
        );

        mailSender.send(message);
    }



    public void sendAdminOtpEmail(String toEmail, String otp, String requestedUsername) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Admin Creation Request - Verification Key from GoMechanic by Yash");

        message.setText(
                "Dear Super Admin,\n\n" +
                        "A request has been made to create a new Admin account on the GoMechanic by Yash platform.\n\n" +
                        "Requested Admin Username: " + requestedUsername + "\n\n" +
                        "To verify and approve this request, please use the following Admin Creation Key:\n\n" +
                        "    " + otp + "\n\n" +
                        "🕒 This key is valid for 5 minutes only.\n\n" +
                        "⚠️ Please do not share this key with anyone. This is a sensitive authorization step.\n\n" +
                        "If you did not initiate or expect this request, you may safely ignore this email.\n\n" +
                        "Thank you for securing the platform.\n\n" +
                        "Best regards,\n" +
                        "GoMechanic by Yash\n" +
                        "System Security Team"
        );

        mailSender.send(message);
    }






}

