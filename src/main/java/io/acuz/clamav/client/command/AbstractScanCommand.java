package io.acuz.clamav.client.command;

import io.acuz.clamav.client.dto.ScanResult;
import io.acuz.clamav.client.exception.InvalidResponseException;
import io.acuz.clamav.client.exception.ScanFailureException;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public abstract class AbstractScanCommand extends AbstractCommand<ScanResult> {
    private static final Pattern RESPONSE_OK = Pattern.compile("(.+) OK$", Pattern.UNIX_LINES);
    private static final Pattern RESPONSE_VIRUSES_FOUND = Pattern.compile("(.+) FOUND$", Pattern.MULTILINE | Pattern.UNIX_LINES);
    private static final Pattern RESPONSE_ERROR = Pattern.compile("(.+) ERROR", Pattern.UNIX_LINES);
    private static final Pattern RESPONSE_VIRUS_FOUND_LINE = Pattern.compile("(.+: )?(.+): (.+) FOUND$", Pattern.UNIX_LINES);


    @Override
    ScanResult parse(String response) {
        var builder = ScanResult.builder();

        if (RESPONSE_OK.matcher(response).matches()) {
            return builder.build();
        } else if (RESPONSE_VIRUSES_FOUND.matcher(response).matches()) {
            var foundViruses = response.split("\n");

            //stream: Eicar-Signature FOUND
            for (String foundVirus : foundViruses) {
                var matcher = RESPONSE_VIRUS_FOUND_LINE.matcher(foundVirus);

                if (matcher.matches()) {
                    var fileName = (StringUtils.hasText(matcher.group(1)) ? matcher.group(1) + ":" : "").concat(matcher.group(2)).trim();
                    var virusName = matcher.group(3).trim();

                    builder.add(fileName, virusName);
                }
            }

            return builder.build();
        } else if (RESPONSE_ERROR.matcher(response).matches()) {
            throw new ScanFailureException(response);
        } else {
            throw new InvalidResponseException(response);
        }
    }
}
