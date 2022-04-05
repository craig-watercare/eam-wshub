package ch.cern.cmms.wshub.rest.controllers;

import ch.cern.cmms.wshub.rest.WSHubController;
import ch.cern.cmms.wshub.rest.annotations.ApiInforAuthentication;
import ch.cern.cmms.wshub.rest.annotations.ApiInforResponse;
import ch.cern.cmms.wshub.rest.tools.AuthenticationTools;
import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.services.workorders.entities.WorkOrderSchedule;
import ch.cern.eam.wshub.core.tools.InforException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.math.BigInteger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/workorders/schedulelabor")
@Api(tags={"Work Orders Labor Scheduling"})
public class WorkScheduleController extends WSHubController {

    @Inject
    private AuthenticationTools authentication;

    @Inject
    private InforClient inforClient;

    @POST
    @Path("/{workOrderNumber}")
    @Produces("application/json")
    @ApiOperation("Schedule Work Order Labor")
    @ApiInforAuthentication
    @ApiInforResponse
    public Response createWorkOrderScheduledLabor(@PathParam("workOrderNumber") String workOrderNumber, WorkOrderSchedule scheduleLabor) {
        try {
            scheduleLabor.setWorkOrderNumber(workOrderNumber);
            return ok(inforClient.getWorkOrderScheduleService().createWorkOrderSchedule(authentication.getInforContext(), scheduleLabor));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @PUT
    @Path("/{workOrderNumber}")
    @Produces("application/json")
    @ApiOperation("Update Scheduled Work Order Labor")
    @ApiInforAuthentication
    @ApiInforResponse
    public Response updateWorkOrderScheduledLabor(@PathParam("workOrderNumber") String workOrderNumber, WorkOrderSchedule scheduleLabor) {
        try {
            scheduleLabor.setWorkOrderNumber(workOrderNumber);
            return ok(inforClient.getWorkOrderScheduleService().updateWorkOrderSchedule(authentication.getInforContext(), scheduleLabor));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @DELETE
    @Path("/{workOrderNumber}")
    @Produces("application/json")
    @ApiOperation("Delete Scheduled Work Order Labor")
    @ApiInforAuthentication
    @ApiInforResponse
    public Response deleteWorkOrderScheduledLabor(
        @PathParam("workOrderNumber") String workOrderNumber,
        @QueryParam("activityCode") BigInteger activityCode,
        @QueryParam("workOrderScheduleCode") String workOrderScheduleCode ) {
        try {
            return ok(inforClient.getWorkOrderScheduleService().deleteWorkOrderSchedule(authentication.getInforContext(), workOrderNumber, activityCode, workOrderScheduleCode));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

}
